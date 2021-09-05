package org.harden.coder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


public class ReflectSupportGenericType<T extends Comparable<? extends T>> {

    public static void main(String[] args) {
        TypeVariable<? extends Class<? extends ReflectSupportGenericType>>[] typeParameters
                = ReflectSupportGenericType.class.getTypeParameters();

        for (TypeVariable<? extends Class<? extends ReflectSupportGenericType>> typeParameter : typeParameters) {
            Type[] bounds = typeParameter.getBounds();
            for (Type type:bounds){
                if(type instanceof ParameterizedType){
                    ParameterizedType parameterizedType =(ParameterizedType)type;
                    System.out.println(parameterizedType.getRawType());
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();

                }
                System.out.println(type);
            }
        }
    }

}
