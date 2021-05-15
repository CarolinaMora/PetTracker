package com.teammovil.domain

class Result<S, E> (
    var success: S?,
    var error: E?
){
    var valid: Boolean = false

    init{
        valid = success!=null
    }
}