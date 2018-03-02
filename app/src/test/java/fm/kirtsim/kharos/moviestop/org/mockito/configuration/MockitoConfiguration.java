package fm.kirtsim.kharos.moviestop.org.mockito.configuration;


import org.mockito.configuration.DefaultMockitoConfiguration;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import io.reactivex.Single;

/**
 * Created by kharos on 23/02/2018
 */

public final class MockitoConfiguration extends DefaultMockitoConfiguration {

    @Override
    public Answer<Object> getDefaultAnswer() {
        return new ReturnsEmptyValues() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Class<?> type = invocation.getMethod().getReturnType();

                if (type.isAssignableFrom(Single.class))
                    return Single.error(createException(invocation));
                return super.answer(invocation);
            }
        };
    }

    private RuntimeException createException(InvocationOnMock invocation) {
        return new RuntimeException("No mock defined for invocation " +
            invocation.toString());
    }
}
