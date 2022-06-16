package com.x.commons.fastjson;

import java.io.Serializable;

public interface IEnum<T extends Serializable> {

    T getValue();
    
}