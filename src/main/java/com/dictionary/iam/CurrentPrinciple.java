package com.dictionary.iam;


public interface CurrentPrinciple {
    Principle getPrinciple();

    User getUser() throws Exception;

}
