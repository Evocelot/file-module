package hu.evocelot.modulebase.common.rest.restclient.provider;

import hu.evocelot.modulebase.common.rest.header.ProjectHeader;
import hu.icellmobilsoft.coffee.module.mp.restclient.RestClientPriority;
import hu.evocelot.modulebase.dto.constant.IHttpHeaderConstants;

import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;

/**
 * Project Rest Client request setting filter.
 *
 * @author mark.danisovszky
 * @since 0.1.0
 */
@Priority(value = RestClientPriority.REQUEST_SETTING - 100)
public class ProjectSettingClientRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) {
        Instance<ProjectHeader> instance = CDI.current().select(ProjectHeader.class);
        if (instance.isResolvable()) {
            ProjectHeader projectHeader = instance.get();
            if (!requestContext.getHeaders().containsKey(IHttpHeaderConstants.HEADER_SESSION_TOKEN)) {
                requestContext.getHeaders().add(IHttpHeaderConstants.HEADER_SESSION_TOKEN, projectHeader.getSessionToken());
            }
        }
    }
}
