package io.github.mat3e;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HelloServiceTest {
    private final static String WELCOME = "Hello";
    private final static String FALLBACK_ID_WELCOME = "Hola";
    @Test
    public void test_prepareGreeting_nullName_returnsGreetingFallbackName()  {
        // given

        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService();

        // name
        var result = SUT.prepareGreeting(null, "-1");
        // then
        assertEquals(WELCOME + " "+ HelloService.FALLBACK_NAME + "!", result);
    }


    @Test
    public void test_prepareGreeting_name_returnsGreetingWithName() {
        // given
        var SUT = new HelloService();
        var name = "test";
        // name
        var result = SUT.prepareGreeting(name, "-1");
        // then
        assertEquals(WELCOME + " " + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang()  {
        // given

        var mockRepository = getMockRepository();

        var SUT = new HelloService(mockRepository);
        var name = "test";
        // name
        var result = SUT.prepareGreeting(null, null);
        // then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang()  {
        // given

        var mockRepository = new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };

        var SUT = new HelloService(mockRepository);
        var name = "test";
        // name
        var result = SUT.prepareGreeting(null, "-1");
        // then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang()  {
        // given

        var mockRepository = getMockRepository();

        var SUT = new HelloService(mockRepository);
        var name = "test";
        // name
        var result = SUT.prepareGreeting(null, "abc");
        // then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository getMockRepository() {
        return new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                if(id.equals(HelloService.FALLBACK_LANG.getId())){
                    return Optional.of(new Lang(null,FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }


    private LangRepository alwaysReturningHelloRepository() {
        return new LangRepository(){
            @Override
            Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(777, WELCOME, ""));
            }
        };
    }
}
