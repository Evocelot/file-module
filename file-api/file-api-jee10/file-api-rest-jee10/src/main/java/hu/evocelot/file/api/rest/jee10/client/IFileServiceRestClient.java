package hu.evocelot.file.api.rest.jee10.client;

import jakarta.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import hu.evocelot.file.api.common.path.FileRestPath;
import hu.evocelot.file.api.rest.jee10.rest.IFileServiceRest;

/**
 * JakartaEE 10 rest client for the file service.
 *
 * @author mark.danisovszky
 * @since 0.2.0
 */
@RegisterRestClient(configKey = "fileService")
@Path(FileRestPath.FILE_SERVICE_BASE_PATH)
public interface IFileServiceRestClient extends IFileServiceRest {

}
