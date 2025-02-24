## Introduction
* This library contains several packages which can be useful while building web services.
* Supported versions: **Java 11+** (the _certificate_ package need **JDK** in order to work)
* Run `mvn clean package` to generate the jar file

## Validation (com.bencesoft.commons.validation)

The package contains **configurable** bean validations for **validating Java objects using annotations**. The validations have default options which can be set manually. Currently supported validations with their options:

* **Email**
    * `nullable() default false`
    * `allowedSpecialChars() default "._-"`

* **NonNegative**
    * `nullable() default false`

* **NonNull**

* **NotBlank**
    * `nullable() default false`

* **PersonName**
    * `nullable() default false`

* **StrongPassword**
    * `nullable() default false`
    * `minLength() default 8`
    * `needUppercase() default true`
    * `needLowercase() default true`
    * `needDigit() default true`
    * `needSpecialChar() default true`
    * `allowedSpecialChars() default "._-!?#@&%*^"`
 
* **Username**
    * `nullable() default false`
    * `minLength() default 5`
    * `allowUppercase() default false`
    * `allowedSpecialChars() default "_."`
      
### Examples
Here is an example for validating a _Patch_ request in a REST controller. Since we might update only specific fields, the validations should allow `null` values.
```
public class UserPatchRequest {

    @Username(nullable = true, allowedSpecialChars = "._-")
    private String username;

    @Email(nullable = true)
    private String email;

    @StrongPassword(nullable = true, minLength = 10, needSpecialChar = false)
    private String password;

    @PersonName(nullable = true)
    private String firstName;

    ...
}

public ResponseEntity<UserResponse> patchUser(Long userId, @RequestBody @Validated UserPatchRequest userPatchRequest) {
   ...
}
```

