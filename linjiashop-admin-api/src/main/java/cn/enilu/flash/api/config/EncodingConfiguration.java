package cn.enilu.flash.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 字符编码配置
 * 解决中文乱码问题
 *
 * @author enilu
 */
@Configuration
public class EncodingConfiguration {

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> encodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        // 强制使用 UTF-8 编码
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        // 过滤所有请求
        registrationBean.addUrlPatterns("/*");
        // 最高优先级
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.setName("CharacterEncodingFilter");

        return registrationBean;
    }
}
