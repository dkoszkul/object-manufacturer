# object-manufacturer
[![Build Status](https://travis-ci.org/dkoszkul/object-manufacturer.svg?branch=master)](https://travis-ci.org/dkoszkul/object-manufacturer)
[![Test Coverage](https://codecov.io/github/dkoszkul/object-manufacturer/coverage.svg?branch=master)](https://codecov.io/gh/dkoszkul/object-manufacturer)

Library writing in progress...  

# Object manufacturer

## Introduction

> Object manufacturer is a Java library, which can be used during testing process. It generated objects with mocked data so you don't need to use any builders or setter methods to fulfill the objects you want to use to test your business logic.

## Support
Library supports:  

|             POJO            	|                  ENTITY                 	|
|:---------------------------:	|:---------------------------------------:	|
| primitives                  	| everything from POJO mode (in progress) 	|
| simple and extended objects 	| bidirectional @OneToOne                 	|
| Arrays, Lists, Maps         	|                                         	|
| Enums                       	|                                         	|

## Code Samples

### POJO generation
> 
```java
public class MyObject {
  private Map<String, SomeOtherObject> map;
  private List<ExtendedListOfStringsObject> list;
  private String string;
  private String[] array;
  private long simpleLong;
  
  // getters and setters
}

public class ExampleTest {
  private DataGenerator generator = ManufacturerFactory.getGeneratorInstance(GenerationMode.POJO);
  
  @Test
  public void exampleTest() {
    MyObject object = generator.generateObject(MyObject.class);
    
    // your test logic
  }
}
```

### ENTITY generation
```java
@Entity
public class MyEntityObject {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
 
   @Column(name = "first_name", length = 100)
   private String firstName;
 
   @Column(name = "last_name", length = 50)
   private String lastName;
  
  // getters and setters
}

public class ExampleTest {
  private DataGenerator generator = ManufacturerFactory.getGeneratorInstance(GenerationMode.ENTITY);
  
  @Test
  public void exampleTest() {
    MyEntityObject object = generator.generateObject(MyEntityObject.class);
    
    // your test logic
  }
}
```