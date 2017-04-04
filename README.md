# Easy Rest
## 

**Designed for small or medium servers and fast development.**
![](https://www.dbgsoftware.tech/EasyRest/EasyRestInfo.png)
 
#### Steps before service:
 * Request attribute configuration. e.g:charset
 * Request method validate.
 * Parameters value inject.
 * Permission check.
 * Missing fields check.
 * Customized check.(if required) 
 * ... 
 * Other customized step.(if required) 
 * ...
 * Transaction prepared.(if required) 

#### Steps after service:
 * History record.(if required) 
 * ...
 * Other customized step.(if required) 
 * ...
 * Transaction commit or rollback.(if required) 

#### Sync request:
 * Return with the result.
 
#### Async request:
 * Submit to job pool and return jobId.
 * Query job status.
 * Get job result.
 
## Quick start
### How to build a simple rest server?
#### Just 3 Files: 
* Startup.java
> This java file is for the whole system that can config some parameters for this server.
* ExampleModel.java
> This is the rest model that defined what parameters we want.
* ExampleServiceImpl.java
> This is the implement of the rest.

##### Startup.java

```java

package com.easyrest.business;

import com.easyrest.framework.easyrest.EasyRest;
import com.easyrest.framework.easyrest.SystemStartupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Startup implements SystemStartupService {

    private static final EasyRest EASY_REST = new EasyRest();

    @Override
    public void init(){
        EASY_REST
                .setEnabledAutoTransaction(false)
                .setCrossAllow("*")
                .setSystemName("MyRestServer");
    }

}
```

**setEnabledAutoTransaction(false)** is the auto transaction switch. If set true, and add **@TransactionRequired** on the model, the transaction will be auto managed by EasyRest, the transaction can auto start and commit or rollback. You also should add the config in spring applicationContext.xml like:
```
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
```
There are many other configurations will be mentioned on other chapter.


##### ExampleModel.java

```java
package com.easyrest.business.model.request;

import com.easyrest.business.services.business.rest.HomeServiceImpl;
import com.easyrest.framework.core.annotations.bean.BindService;
import com.easyrest.framework.core.annotations.method.Get;
import com.easyrest.framework.core.annotations.parameter.AllDefined;
import com.easyrest.framework.core.model.request.AbstractRequestModel;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.exception.ConditionMissingException;

@Get({"/example"})
@AllDefined
@BindService(HomeServiceImpl.class)
public class ExampleModel extends AbstractRequestModel {

    private String code;
    private String url;
    private String message;

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException {
        if (url.equals("123")) throw new ConditionMissingException("url can not be 123");
    }
}

```