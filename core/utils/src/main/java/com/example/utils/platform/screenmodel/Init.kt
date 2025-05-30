package com.example.utils.platform.screenmodel

interface Init<D:ScreenDependencies>{
    fun init(deps:D)
}