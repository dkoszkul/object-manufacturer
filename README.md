# object-manufacturer
[![Build Status](https://travis-ci.org/dkoszkul/object-manufacturer.svg?branch=master)](https://travis-ci.org/dkoszkul/object-manufacturer)
[![Test Coverage](https://codecov.io/github/dkoszkul/object-manufacturer/coverage.svg?branch=master)](https://codecov.io/gh/dkoszkul/object-manufacturer)

Library writing in progress...  

# Object manufacturer

## Introduction

> Object manufacturer is a Java library, which can be used during testing process. It generated objects with mocked data so you don't need to use any builders or setter methods to fulfill the objects you want to use to test your business logic.

## Code Samples

> ```java
public class ExampleTest {
  private DataGenerator generator = ManufacturerFactory.getGeneratorInstance(GenerationMode.POJO);
  @Test
  public void exampleTest() {
    MyObject object = generator.generateObject(MyObject.class);
}
``` 