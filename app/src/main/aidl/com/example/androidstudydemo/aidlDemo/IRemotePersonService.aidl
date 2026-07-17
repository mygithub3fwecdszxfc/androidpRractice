package com.example.androidstudydemo.aidlDemo;
import com.example.androidstudydemo.aidlDemo.MyPerson;

interface IRemotePersonService {
    int add(int a, int b);
    MyPerson getPersonInfo();
}