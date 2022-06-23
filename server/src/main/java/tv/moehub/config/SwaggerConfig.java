package tv.moehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author wangrong
 * @date 2022/6/23 10:51
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)//1.以OAS_30标准构建Docket配置类
                .apiInfo(apiInfo())//2.调用apiInfo方法，配置API的基本信息
                .select()//3.调用select方法，配置接口扫描路径
                .build();//4.调用build方法，生成Docket配置类
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MoeHub API")
                .description("MoeHub视频网站接口文档")
                .version("1.0")
                .build();
    }
}
