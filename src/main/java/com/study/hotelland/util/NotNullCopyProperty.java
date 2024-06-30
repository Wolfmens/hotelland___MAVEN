package com.study.hotelland.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class NotNullCopyProperty {


    public static void copyNonNullProperties(Object source, Object destination, String[] ignoreProperties){
        Set<String> ignorePropertiesSet = getNullPropertyNames(source);
        ignorePropertiesSet.addAll(Arrays.asList(ignoreProperties));
        BeanUtils.copyProperties(source, destination, ignorePropertiesSet.toArray(String[]::new));
    }


    private static Set<String> getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames;
    }
}
