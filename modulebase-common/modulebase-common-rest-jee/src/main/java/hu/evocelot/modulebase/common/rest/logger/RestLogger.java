package hu.evocelot.modulebase.common.rest.logger;

import hu.icellmobilsoft.coffee.rest.log.BaseRestLogger;
import hu.evocelot.modulebase.dto.constant.IHttpHeaderConstants;
import hu.evocelot.modulebase.common.rest.filter.CustomPriorities;
import jakarta.annotation.Priority;
import jakarta.ws.rs.ext.Provider;

/**
 * REST request-response logger.
 *
 * @author mark.danisovszky.
 * @since 0.1.0
 */
@Provider
@Priority(CustomPriorities.PRE_AUTHENTICATION)
public class RestLogger extends BaseRestLogger {

    @Override
    public String sessionKey() {
        return IHttpHeaderConstants.HEADER_SID;
    }
}
