package com.fresco.business.config;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.omnifaces.util.Faces;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class RestoreViewListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        // NOOP
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // TODO Set this information in a per session basis, so we'll need to inject a session bean
        Faces.setContextAttribute("locale", SupportedCountry.NICARAGUA.getLocale());
        Faces.setContextAttribute("localDatePattern", SupportedCountry.NICARAGUA.getPatternForDate());
        Faces.setContextAttribute("localDateTimePattern", SupportedCountry.NICARAGUA.getPatternForTimestampMillis());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
