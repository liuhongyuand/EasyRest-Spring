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