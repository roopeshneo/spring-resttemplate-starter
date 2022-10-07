# spring-resttemplate-starter
Starter project that explains how you should use a RestTemplate in a  spring boot application and few best practices.

During my interview experience I see a lot of initial rounds are around developing a mock project which calls some api and manipulate data.
The goal of this project is to help set up a starting point for such assignments. 

* Use RestTemplateBuilder instead of creating your own RestTemplate instance. Ref: https://medium.com/@TimvanBaarsen/spring-boot-why-you-should-always-use-the-resttemplatebuilder-to-create-a-resttemplate-instance-d5a44ebad9e9
* Use ResponseErrorHandler to handle RestTemplate api response. Ref: https://www.baeldung.com/spring-rest-template-error-handling
* Unit test template to test any such api calls.
