package me.kyunghwan.todos.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotEmpty
    private String clientId;

    @NotEmpty
    private String clientSecret;

}
