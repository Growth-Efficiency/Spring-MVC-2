package hello.login;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// ArgumentResolver 사용하기.
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
		resolvers.add(new LoginMemberArgumentResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor()) // LogInterceptor 미리 만든 인터셉터 등록.
				.order(1) //인터셉터의 호출 순서를 지정. 낮을수록 먼저 실행.
				.addPathPatterns("/**") // 인터셉터를 적용할 URL 패턴을 지정한다.
				.excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉터는 여기서 제외 할 패턴을 지정 가능.

		registry.addInterceptor(new LoginCheckInterceptor())
				.order(2)
				.addPathPatterns("/**")
				.excludePathPatterns(
						"/", "/members/add", "/login", "/logout",
						"/css/**", "/*.ico", "/error"
				);
	}

//	@Bean
	public FilterRegistrationBean logFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LogFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*");

		return filterRegistrationBean;
	}

//	@Bean
	public FilterRegistrationBean loginCheckFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new LoginCheckFilter());
		filterRegistrationBean.setOrder(2);
		filterRegistrationBean.addUrlPatterns("/*");

		return filterRegistrationBean;
	}


}
