package com.sxb.lin.hibernate.validator;

import com.sxb.lin.hibernate.validator.annotation.MapCheck;
import com.sxb.lin.hibernate.validator.dto.ValidResult;
import com.sxb.lin.hibernate.validator.proxy.ConstraintValidatorProxy;
import com.sxb.lin.hibernate.validator.proxy.ConstraintValidatorSelectProxy;
import com.sxb.lin.hibernate.validator.proxy.ConstraintValidatorsProxy;
import org.hibernate.validator.constraints.*;
import org.hibernate.validator.internal.constraintvalidators.bv.*;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArray;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForCollection;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForMap;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MinValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMaxValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForNumber;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArray;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCollection;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForMap;
import org.hibernate.validator.internal.constraintvalidators.hv.LengthValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.LuhnCheckValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractConstraintValidator<T> implements ConstraintValidator<MapCheck, T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractConstraintValidator.class);

    private MapCheck annotation;

    private List<ConstraintValidatorProxy> initValidators;

    private boolean isHaveNotNullValidator = false;//优先验证非空验证器

    public void initialize(MapCheck constraintAnnotation) {
        initValidators = new ArrayList<ConstraintValidatorProxy>();
        annotation = constraintAnnotation;
        this.initValidators();
    }

    private void initValidators(){
        this.initNotNullValidator();//带有非空验证
        this.initNotBlankValidator();//带有非空验证
        this.initNotEmptyValidator();//带有非空验证
        this.initNullValidator();
        this.initSizeValidator();
        this.initMaxValidator();
        this.initMinValidator();
        this.initAssertFalseValidator();
        this.initAssertTrueValidator();
        this.initDecimalMaxValidator();
        this.initDecimalMinValidator();
        this.initPatternValidator();
        this.initDigitsValidator();
        this.initPastValidator();
        this.initFutureValidator();
        this.initRangeValidator();
        this.initCreditCardNumberValidator();
        this.initLengthValidator();
        this.initURLValidator();
        this.initLuhnCheckValidator();
        this.initEmailValidator();
        this.initCustomValidator();
    }

    private boolean isExist(Object[] array){
        if(array != null && array.length > 0){
            return true;
        }
        return false;
    }

    protected boolean doValid(Map<String, Object> map, ConstraintValidatorContext context) {
        String key = annotation.key();
        Object value = map.get(key);
        StringBuilder builder = new StringBuilder();
        boolean isValid = true;
        for(int i = 0, len = initValidators.size(); i < len; i++){
            ConstraintValidatorProxy validator = initValidators.get(i);
            ValidResult valid = validator.isValid(value, context, map, key);
            if(!valid.isValid()){
                isValid = false;
                if(builder.length() > 0) builder.append(",");
                builder.append(valid.getMessage());
            }
        }
        
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(builder.toString()).addConstraintViolation();
        }
        return isValid;
    }

    private void initNotNullValidator(){
        NotNull[] notNulls = annotation.notNull();
        if(isExist(notNulls)){
            for(NotNull notNull : notNulls){
                NotNullValidator validator = new NotNullValidator();
                validator.initialize(notNull);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(notNull.message());
                
                initValidators.add(proxy);
                isHaveNotNullValidator = true;
            }
        }
    }

    private void initNullValidator(){
        Null[] nuLLs = annotation.nuLL();
        if(isExist(nuLLs)){
            for(Null nuLL : nuLLs){
                NullValidator validator = new NullValidator();
                validator.initialize(nuLL);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(nuLL.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initSizeValidator(){
        Size[] sizes = annotation.size();
        if(isExist(sizes)){
            for(Size size : sizes){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(size.message());
                
                SizeValidatorForArray sizeValidatorForArray = new SizeValidatorForArray();
                sizeValidatorForArray.initialize(size);
                proxy.addValidator(Array.class, sizeValidatorForArray);
                
                SizeValidatorForCollection sizeValidatorForCollection = new SizeValidatorForCollection();
                sizeValidatorForCollection.initialize(size);
                proxy.addValidator(Collection.class, sizeValidatorForCollection);
                
                SizeValidatorForMap sizeValidatorForMap = new SizeValidatorForMap();
                sizeValidatorForMap.initialize(size);
                proxy.addValidator(Map.class, sizeValidatorForMap);
                
                SizeValidatorForCharSequence sizeValidatorForCharSequence = new SizeValidatorForCharSequence();
                sizeValidatorForCharSequence.initialize(size);
                proxy.addValidator(CharSequence.class, sizeValidatorForCharSequence);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initMaxValidator(){
        Max[] maxs = annotation.max();
        if(isExist(maxs)){
            for(Max max : maxs){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(max.message());
                
                MaxValidatorForCharSequence maxValidatorForCharSequence = new MaxValidatorForCharSequence();
                maxValidatorForCharSequence.initialize(max);
                proxy.addValidator(CharSequence.class, maxValidatorForCharSequence);
                
                MaxValidatorForNumber maxValidatorForNumber = new MaxValidatorForNumber();
                maxValidatorForNumber.initialize(max);
                proxy.addValidator(Number.class, maxValidatorForNumber);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initMinValidator(){
        Min[] mins = annotation.min();
        if(isExist(mins)){
            for(Min min : mins){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(min.message());
                
                MinValidatorForCharSequence minValidatorForCharSequence = new MinValidatorForCharSequence();
                minValidatorForCharSequence.initialize(min);
                proxy.addValidator(CharSequence.class, minValidatorForCharSequence);
                
                MinValidatorForNumber minValidatorForNumber = new MinValidatorForNumber();
                minValidatorForNumber.initialize(min);
                proxy.addValidator(Number.class, minValidatorForNumber);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initAssertFalseValidator(){
        AssertFalse[] assertFalses = annotation.assertFalse();
        if(isExist(assertFalses)){
            for(AssertFalse assertFalse : assertFalses){
                AssertFalseValidator validator = new AssertFalseValidator();
                validator.initialize(assertFalse);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(assertFalse.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initAssertTrueValidator(){
        AssertTrue[] assertTrues = annotation.assertTrue();
        if(isExist(assertTrues)){
            for(AssertTrue assertTrue : assertTrues){
                AssertTrueValidator validator = new AssertTrueValidator();
                validator.initialize(assertTrue);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(assertTrue.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initDecimalMaxValidator(){
        DecimalMax[] decimalMaxs = annotation.decimalMax();
        if(isExist(decimalMaxs)){
            for(DecimalMax decimalMax : decimalMaxs){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(decimalMax.message());
                
                DecimalMaxValidatorForCharSequence decimalMaxValidatorForCharSequence = new DecimalMaxValidatorForCharSequence();
                decimalMaxValidatorForCharSequence.initialize(decimalMax);
                proxy.addValidator(CharSequence.class, decimalMaxValidatorForCharSequence);
                
                DecimalMaxValidatorForNumber decimalMaxValidatorForNumber = new DecimalMaxValidatorForNumber();
                decimalMaxValidatorForNumber.initialize(decimalMax);
                proxy.addValidator(Number.class, decimalMaxValidatorForNumber);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initDecimalMinValidator(){
        DecimalMin[] decimalMins = annotation.decimalMin();
        if(isExist(decimalMins)){
            for(DecimalMin decimalMin : decimalMins){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(decimalMin.message());
                
                DecimalMinValidatorForCharSequence decimalMinValidatorForCharSequence = new DecimalMinValidatorForCharSequence();
                decimalMinValidatorForCharSequence.initialize(decimalMin);
                proxy.addValidator(CharSequence.class, decimalMinValidatorForCharSequence);
                
                DecimalMinValidatorForNumber decimalMinValidatorForNumber = new DecimalMinValidatorForNumber();
                decimalMinValidatorForNumber.initialize(decimalMin);
                proxy.addValidator(Number.class, decimalMinValidatorForNumber);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initPatternValidator(){
        Pattern[] patterns = annotation.pattern();
        if(isExist(patterns)){
            for(Pattern pattern : patterns){
                PatternValidator validator = new PatternValidator();
                validator.initialize(pattern);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(pattern.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initDigitsValidator(){
        Digits[] digitses = annotation.digits();
        if(isExist(digitses)){
            for(Digits digits : digitses){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(digits.message());
                
                DigitsValidatorForCharSequence digitsValidatorForCharSequence = new DigitsValidatorForCharSequence();
                digitsValidatorForCharSequence.initialize(digits);
                proxy.addValidator(CharSequence.class, digitsValidatorForCharSequence);
                
                DigitsValidatorForNumber digitsValidatorForNumber = new DigitsValidatorForNumber();
                digitsValidatorForNumber.initialize(digits);
                proxy.addValidator(Number.class, digitsValidatorForNumber);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initPastValidator(){
        Past[] pasts = annotation.past();
        if(isExist(pasts)){
            for(Past past : pasts){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(past.message());
                
                PastValidatorForLong validator = new PastValidatorForLong();
                validator.initialize(past);
                proxy.addValidator(Number.class, validator);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initFutureValidator(){
        Future[] futures = annotation.future();
        if(isExist(futures)){
            for(Future future : futures){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(future.message());
                
                FutureValidatorForLong validator = new FutureValidatorForLong();
                validator.initialize(future);
                proxy.addValidator(Number.class, validator);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initNotBlankValidator(){
        NotBlank[] notBlanks = annotation.notBlank();
        if(isExist(notBlanks)){
            for(NotBlank notBlank : notBlanks){
                NotBlankValidator validator = new NotBlankValidator();
                validator.initialize(notBlank);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(notBlank.message());
                
                initValidators.add(proxy);
                isHaveNotNullValidator = true;
            }
        }
    }

    private void initNotEmptyValidator(){
        NotEmpty[] notEmptys = annotation.notEmpty();
        if(isExist(notEmptys)){
            for(NotEmpty notEmpty : notEmptys){
                ConstraintValidatorSelectProxy proxy = new ConstraintValidatorSelectProxy();
                proxy.setMessage(notEmpty.message());

                NotEmptyValidatorForCharSequence notEmptyValidatorForCharSequence = new NotEmptyValidatorForCharSequence();
                notEmptyValidatorForCharSequence.initialize(notEmpty);
                proxy.addValidator(CharSequence.class, notEmptyValidatorForCharSequence);

                NotEmptyValidatorForArray notEmptyValidatorForArray = new NotEmptyValidatorForArray();
                notEmptyValidatorForArray.initialize(notEmpty);
                proxy.addValidator(Array.class, notEmptyValidatorForArray);

                NotEmptyValidatorForCollection notEmptyValidatorForCollection = new NotEmptyValidatorForCollection();
                notEmptyValidatorForCollection.initialize(notEmpty);
                proxy.addValidator(Collection.class, notEmptyValidatorForCollection);

                NotEmptyValidatorForMap notEmptyValidatorForMap = new NotEmptyValidatorForMap();
                notEmptyValidatorForMap.initialize(notEmpty);
                proxy.addValidator(Map.class, notEmptyValidatorForMap);

                initValidators.add(proxy);
                isHaveNotNullValidator = true;
            }
        }
    }

    private void initRangeValidator(){
        Range[] ranges = annotation.range();
        if(isExist(ranges)){
            for(Range range : ranges){
                Class<?> clazz = range.annotationType();
                Min min = clazz.getAnnotation(Min.class);
                this.overridesRangeToMin(range, min);
                Max max = clazz.getAnnotation(Max.class);
                this.overridesRangeToMax(range, max);
                
                ConstraintValidatorsProxy proxy = new ConstraintValidatorsProxy();
                proxy.setMessage(range.message());
                
                ConstraintValidatorSelectProxy minProxy = new ConstraintValidatorSelectProxy();
                MinValidatorForNumber minValidatorForNumber = new MinValidatorForNumber();
                minValidatorForNumber.initialize(min);
                minProxy.addValidator(Number.class, minValidatorForNumber);
                proxy.addValidator(minProxy);
                
                ConstraintValidatorSelectProxy maxProxy = new ConstraintValidatorSelectProxy();
                MaxValidatorForNumber maxValidatorForNumber = new MaxValidatorForNumber();
                maxValidatorForNumber.initialize(max);
                maxProxy.addValidator(Number.class, maxValidatorForNumber);
                proxy.addValidator(maxProxy);
                
                initValidators.add(proxy);
            }
        }
    }

    private void initCreditCardNumberValidator(){
        CreditCardNumber[] creditCardNumbers = annotation.creditCardNumber();
        if(isExist(creditCardNumbers)){
            for(CreditCardNumber creditCardNumber : creditCardNumbers){
                Class<?> clazz = creditCardNumber.annotationType();
                LuhnCheck luhnCheck = clazz.getAnnotation(LuhnCheck.class);
                this.overridesCreditCardNumberToLuhnCheck(creditCardNumber, luhnCheck);
                
                LuhnCheckValidator validator = new LuhnCheckValidator();
                validator.initialize(luhnCheck);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(creditCardNumber.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initLengthValidator(){
        Length[] lengths = annotation.length();
        if(isExist(lengths)){
            for(Length length : lengths){
                LengthValidator validator = new LengthValidator();
                validator.initialize(length);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(length.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initURLValidator(){
        URL[] urls = annotation.url();
        if(isExist(urls)){
            for(URL url : urls){
                URLValidator validator = new URLValidator();
                validator.initialize(url);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(url.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initLuhnCheckValidator(){
        LuhnCheck[] luhnChecks = annotation.luhnCheck();
        if(isExist(luhnChecks)){
            for(LuhnCheck luhnCheck : luhnChecks){
                LuhnCheckValidator validator = new LuhnCheckValidator();
                validator.initialize(luhnCheck);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(luhnCheck.message());
                
                initValidators.add(proxy);
            }
        }
    }

    private void initEmailValidator(){
        Email[] emails = annotation.email();
        if(isExist(emails)){
            for(Email email : emails){
                EmailValidator validator = new EmailValidator();
                validator.initialize(email);
                
                ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
                proxy.setValidator(validator);
                proxy.setMessage(email.message());
                
                initValidators.add(proxy);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initCustomValidator(){
        TypeValidator typeValidator = new TypeValidator();
        typeValidator.initialize(annotation);
        
        ConstraintValidatorProxy proxy = new ConstraintValidatorProxy();
        proxy.setValidator(typeValidator);
        proxy.setCustomValidator(true);
        initValidators.add(isHaveNotNullValidator ? 1 : 0, proxy);//类型验证放到前面执行，非空验证除外
        
        Class<? extends AbstractCustomValidator<? extends Annotation, ?>>[] customValidators = annotation.customValidators();
        if(isExist(customValidators)){
            for(Class<? extends AbstractCustomValidator<? extends Annotation, ?>> customValidator : customValidators){
                DefaultListableBeanFactory defaultListableBeanFactory = this.getDefaultListableBeanFactory();
                AbstractCustomValidator customValidatorBean = defaultListableBeanFactory.createBean(customValidator);
                customValidatorBean.initialize(annotation);
                ConstraintValidatorProxy customProxy = new ConstraintValidatorProxy();
                customProxy.setValidator(customValidatorBean);
                customProxy.setCustomValidator(true);
                initValidators.add(customProxy);
            }
        }
    }

    protected abstract DefaultListableBeanFactory getDefaultListableBeanFactory();

    private void overridesRangeToMin(Range range,Min min) {
        this.overridesAttribute(min, "value", range.min());
    }

    private void overridesRangeToMax(Range range,Max max) {
        this.overridesAttribute(max, "value", range.max());
    }

    private void overridesCreditCardNumberToLuhnCheck(CreditCardNumber creditCardNumber,LuhnCheck luhnCheck) {
        this.overridesAttribute(luhnCheck, "ignoreNonDigitCharacters", creditCardNumber.ignoreNonDigitCharacters());
    }

    private void overridesAttribute(Annotation target,String key,Object value) {
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(target);
            Field field = invocationHandler.getClass().getDeclaredField("memberValues");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String,Object> memberValues = (Map<String,Object>) field.get(invocationHandler);
            memberValues.put(key, value);
        } catch (Exception e) {
            throw new RuntimeException("annotation overrides attribute error.",e);
        }
    }
}
