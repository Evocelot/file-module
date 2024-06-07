package hu.evocelot.file.sample.jpaservice.action;

import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import hu.evocelot.file.sample.jpaservice.converter.SampleTypeConverter;
import hu.evocelot.file.sample.jpaservice.service.SampleEntityService;
import hu.icellmobilsoft.coffee.dto.exception.InvalidParameterException;
import hu.icellmobilsoft.coffee.se.api.exception.BaseException;
import hu.evocelot.file.api.sample._1_0.rest.post.SampleResponse;
import hu.evocelot.file.api.sample._1_0.rest.post.SampleType;
import hu.evocelot.file.common.system.rest.action.BaseAction;
import hu.evocelot.file.model.sample.SampleEntity;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

/**
 * Sample get action for reading Sample entity.
 *
 * @author mark.danisovszky
 * @since 0.1.0
 */
@Model
public class JpaSampleGetAction extends BaseAction {

    @Inject
    private Tracer tracer;

    @Inject
    private SampleEntityService sampleEntityService;

    @Inject
    private SampleTypeConverter sampleTypeConverter;

    /**
     * Read the Sample entity based on the id.
     *
     * @param sampleId
     *         - the id of the sample entity.
     * @return - with the {@link SampleResponse}.
     * @throws BaseException
     *         - when error occurs.
     */
    public SampleResponse sample(String sampleId) throws BaseException {
        Span span = tracer.spanBuilder("Sample GET action span").startSpan();

        span.addEvent("test event", Attributes.of(AttributeKey.stringKey("sample.id"), sampleId));

        if (StringUtils.isBlank(sampleId)) {
            throw new InvalidParameterException("The sampleId is null or blank!");
        }

        SampleEntity entity = sampleEntityService.findById(sampleId, SampleEntity.class);

        SampleResponse response = new SampleResponse();
        SampleType sampleType = sampleTypeConverter.convert(entity);
        response.setSample(sampleType);

        handleSuccessResultType(response);

        span.end();

        return response;
    }
}
