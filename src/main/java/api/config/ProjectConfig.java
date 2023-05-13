package api.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:url/local.properties")
public interface ProjectConfig extends Config {
    @Key("base_url")
    String baseUrl();

    @Key("single_user_url")
    String singleUserUrl();

    @Key("register_user_url")
    String registerUserUrl();

}
