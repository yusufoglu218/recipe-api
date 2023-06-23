# Recipe Rest Api

Recipe rest api that includes CRUD (**C**reate, **R**ead, **U**pdate, **D**elete) and getBySearchCriteria operations using spring boot and h2 database.
When application started the scripts in the resources/data.sql is run automatically, and it saves initial data to the h2 database.


## Prerequisites
- Java 11
- [Maven](https://maven.apache.org/guides/index.html)


###  Build and Run application
_GOTO >_ **~/absolute-path-to-directory/recipe-api**  
and try below command in terminal
> **```mvn spring-boot:run```** it will run application as spring boot application

or
> **```mvn clean install```** it will build application and create **jar** file under target directory

Run jar file from below path with given command
> **```java -jar ~/path-to-recipe-service/target/recipe-api-0.0.1-SNAPSHOT.jar```**

### Model class
Below are the model classes which we will store in H2 and perform database operations.

   ```
   public class Recipe {
      Long id;
      String title;
      String directions;
      int yields;
      List<Category> categories;
      List<Ingredient> ingredients
   }

    public class Category {
        private String name;
    }

   public class Ingredient {
       String name;
       String amount;
       String amountQty;
       String amountUnit;
   }
   ```

### Endpoints

#### HTML

|HTTP Method|URL|Description|
|---|---|---|
|`GET`|http://localhost:8080/ | Root page |
|`GET`|http://localhost:8080/swagger-ui/index.html | Swagger UI page |
|`GET`|http://localhost:8080/actuator | Actuator page |

#### H2 Database Console Endpoint
|`GET`|http://localhost:8080/h2-console| H2 database console page |

#### Recipe Endpoints

|HTTP Method|URL|Description|
|---|---|---|
|`POST`|http://localhost:8080/api/v1/recipes | Create new Recipe |
|`PUT`|http://localhost:8080/api/v1/recipes/{id} | Update Recipe by ID |
|`GET`|http://localhost:8080/api/v1/recipes/{id} | Get Recipe by ID |
|`DELETE`|http://localhost:8080/api/v1/recipes/{id} | Delete Recipe by ID |
|`GET`|http://localhost:8080/api/v1/recipes?categoryId=1026&searchString=ingredient:*Onion*,category:*Main*,yields>5&pageNumber=0&pageSize=3&sortBy=title | Get Recipes by criteria with Paging. If there is no criteria then all recipes will be returned.|

#### Category Endpoints

|HTTP Method|URL|Description|
|---|---|---|
|`POST`|http://localhost:8080/api/v1/categories | Create new Category |
|`PUT`|http://localhost:8080/api/v1/categories/{id} | Update Category by ID |
|`GET`|http://localhost:8080/api/v1/categories/{id} | Get Category by ID |
|`DELETE`|http://localhost:8080/api/v1/categories/{id} | Delete Category by ID |
|`GET`|http://localhost:8080/api/v1/categories?pageNumber=0&pageSize=2&sortBy=name | Get Categories by with Paging|


#### Request Body sample for post and put for Recipe operations
```
    {   
        "title": "New chicken dish",
        "directions": "Place zucchini and butter in a 2 quart casserole. Cover with plastic wrap.\\000a ",
        "yields": 6,
        "categories": [
            {
                "id": 1002,
                "name": "Microwave"
            },
            {
                "id": 1003,
                "name": "Vegetables"
            }
        ],
        "ingredients": [
            {
                "name": "Eggs; beaten",
                "amountQty": "3",
                "amountUnit": null
            },
            {
                "name": "Zucchini; cubed 1/2",
                "amountQty": "1",
                "amountUnit": "pound"
            }
        ]
    }
``` 

#### Request Body sample for post and put for Category operations
```
    {
        "name": "New category"
    }
``` 
