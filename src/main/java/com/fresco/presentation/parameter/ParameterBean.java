package com.fresco.presentation.parameter;

import com.fresco.business.parameter.exception.ParameterNotFound;
import com.fresco.business.parameter.logic.ParameterProvider;
import com.fresco.business.parameter.model.Parameter;
import com.fresco.presentation.ScreenMode;
import com.fresco.presentation.security.ChangesMadeByEvent;
import com.fresco.presentation.security.Created;
import com.fresco.presentation.security.Updated;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.omnifaces.util.Messages;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@Named
@ViewScoped
public class ParameterBean implements Serializable {

    @Inject
    ParameterProvider provider;

    @Inject
    @Created
    Event<ChangesMadeByEvent> createdEventProducer;

    @Inject
    @Updated
    Event<ChangesMadeByEvent> updatedEventProducer;

    @NotNull(message = "Please specify a search criteria")
    @Size(max = 100)
    private String criteria;

    @Size(min = 1, max = 400)
    private String value;

    private List<Parameter> results;
    private Parameter selected;
    private String emptyMessage;
    private ScreenMode mode;

    @PostConstruct
    public void init() {
        results = new ArrayList<>();
        emptyMessage = "&nbsp;";
        mode = ScreenMode.SEARCH;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Parameter> getResults() {
        return results;
    }

    public void setResults(List<Parameter> results) {
        this.results = results;
    }

    public Parameter getSelected() {
        return selected;
    }

    public void setSelected(Parameter selected) {
        this.selected = selected;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public ScreenMode getMode() {
        return mode;
    }

    public void setMode(ScreenMode mode) {
        this.mode = mode;
    }

    public boolean isOnSearchingMode() {
        return mode == ScreenMode.SEARCH;
    }

    public boolean isOnEditionMode() {
        return mode == ScreenMode.EDIT;
    }

    public void search() {
        emptyMessage = "No records found!";
        results = provider.findByText(criteria);
    }

    public void edit() {
        mode = ScreenMode.EDIT;

        // -------------------------------------------------------------------------------------------------
        // Get related data
        createdEventProducer.fire(new ChangesMadeByEvent(selected.getCreatedBy(), selected.getCreatedOn()));

        if (selected.getUpdatedBy() != null) {
            updatedEventProducer.fire(new ChangesMadeByEvent(selected.getUpdatedBy(), selected.getUpdatedOn()));
        }
    }

    public void update() throws ParameterNotFound {
        List<String> errors = provider.updateValue(selected.getParameterType(), value);
        errors.forEach(Messages::addGlobalError);
    }

    public void cancel() {
        mode = ScreenMode.SEARCH;
    }

}
