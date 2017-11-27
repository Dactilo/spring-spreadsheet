# Dactilo spring-spreadsheet 
This component will allow your spring-web controllers 
to produce CSV or Microsoft Excel instead of JSON files without writing any code.

## Setup
Add this dependency to your maven project:
```
<dependency>
    <groupId>io.dactilo.sumbawa</groupId>
    <artifactId>spring-spreadsheets</artifactId>
    <version>1.5</version>
</dependency>
```  

## Usage 
### CSV support 
* Add `@EnableCSVFormatter` annotation to your spring-web configuration class.
* Any controller returning a List of beans and producing text/csv files will automatically be serialized into a CSV file (see example bellow)
 
### Excel support 
* Add `@EnableExcelFormatter` annotation to your spring-web configuration class.
* Any controller returning a List of beans and producing vnd.openxmlformats-officedocument.spreadsheetml.sheet files will automatically be serialized into a valid Excel file (see example bellow)

### Example 
Spring-web configuration file: 
```
@Configuration
@EnableWebMvc
@EnableExcelFormatter
class SampleExcelConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public SampleController sampleController() {
        return new SampleController();
    }
}
```

Spring-web controller:
```
@Controller
@RequestMapping("/")
@ResponseStatus(HttpStatus.OK)
class SampleController {
    @RequestMapping(value = "json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ObjectExampleDTO>> json() {
        return new ResponseEntity<>(Collections.singletonList(
                new ObjectExampleDTO("field 1", 2, null, false)
        ), HttpStatus.OK);
    }

    @RequestMapping(value = "csv", method = RequestMethod.GET, produces = "text/csv")
    public ResponseEntity<List<ObjectExampleDTO>> csv() {
        return new ResponseEntity<>(Collections.singletonList(
                new ObjectExampleDTO("field 1", 2, null, false)
        ), HttpStatus.OK);
    }
}
```

ObjectExampleDTO: 
```
public static class ObjectExampleDTO {
    private final String field1;
    private final int field2;
    private final Date field3;
    private final boolean field4;

    @JsonCreator
    public ObjectExampleDTO(@JsonProperty("field1") String field1,
                            @JsonProperty("field2") int field2,
                            @JsonProperty("field3") Date field3,
                            @JsonProperty("field4") boolean field4) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    public String getField1() {
        return field1;
    }

    public int getField2() {
        return field2;
    }

    public Date getField3() {
        return field3;
    }

    public boolean isField4() {
        return field4;
    }
}
```

In the previous example:
* /json controller will produce a valid json file:  
    ``` 
    [{"field1":"field 1","field2":2,"field3":null,"field4":false}]
    ```
* /csv controller will produce a valid CSV file:  
    ``` 
    field1;field2;field3;field4
    field 1;2;;false
    ```
        
### Tweaking 
Several annotations are supported. Suppose you are returning a list of ObjectExampleDTO (see the previous example).
You can add the following annotations to the ObjectExampleDTO class definition getters: 

Annotations                        | Definition
---------------------------------- | -------------
`SpreadsheetColumnIgnore`          | to prevents the column to be created
`SpreadsheetColumnName`            | to override the name of the column (by default, it will use the name of the field)
`SpreadsheetColumnOrder`           | to enforce the order of the columns (the lowest will be on the left)
`SpreadsheetColumnSerializer`      | the custom the way to the object is serialized (by default, toString() will be used). 

## TODO / Next steps 
* CSV / Excel importation support 
## Licence 
See LICENCE.md 
