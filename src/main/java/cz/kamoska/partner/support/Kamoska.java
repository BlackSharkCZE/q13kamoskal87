package cz.kamoska.partner.support;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>To change this template use File | Settings | File Templates.</p>
 * Created with IntelliJ IDEA.<br/>
 * User: blackshark<br/>
 * Date: 14.4.13<br/>
 * Time: 16:26<br/>
 * <br/>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER, ElementType.TYPE})
public @interface Kamoska {
}
