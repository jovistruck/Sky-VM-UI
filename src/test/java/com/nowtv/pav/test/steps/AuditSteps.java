package com.nowtv.pav.test.steps;

import cucumber.api.java.en.Then;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;

public class AuditSteps extends StepBase {
	
	@Then("the audit values are")
	public void the_audit_values_are(List<Map<String, String>> auditRows) {

		for (Map<String, String> row : auditRows) {
			awaitTextPresence(row.get("Field"));
			awaitTextPresence(row.get("Old value"));
			awaitTextPresence(row.get("New value"));
		}

		Set<ImmutableTriple<String, String, String>> expected = new HashSet<>();
		for (Map<String, String> row : auditRows) {
			expected.add(new ImmutableTriple<>(row.get("Field"), row.get("Old value"), row.get("New value")));
		}
		
		Set<ImmutableTriple<String, String, String>> actual = new HashSet<>();
		FluentList<FluentWebElement> values = find("td", withClass("ng-binding"));
		for (int i=0; i<values.size(); i+=3) {
			actual.add(new ImmutableTriple<>(values.get(i).getText(), values.get(i + 1).getText(), values.get(i + 2).getText()));
		}

		assertThat(actual).containsAll(expected);
		screenshotTrail();
	}

    @Then("^the audit history field (state|exported|.+|doNotCheck) changes from (CANCELLED|PENDING|READY|.+|doNotCheck) to (CANCELLED|PENDING|READY|.+|doNotCheck)$")
    public void audit_state_changes_to(String state, String oldValue, String newValue) {
		awaitElementDisplayed("td:nth-child(1)");

		FluentWebElement fieldElement = findFirst("td:nth-child(1)");
        FluentWebElement oldFieldValueElement = findFirst("td:nth-child(2)");
        FluentWebElement newFieldValueElement = findFirst("td:nth-child(3)");

        if(!state.equalsIgnoreCase("doNotCheck"))
            assertThat(fieldElement.getText()).isEqualTo(state);
        if(!oldValue.equalsIgnoreCase("doNotCheck"))
            assertThat(oldFieldValueElement.getText()).isEqualTo(oldValue);
        if(!newValue.equalsIgnoreCase("doNotCheck"))
            assertThat(newFieldValueElement.getText()).isEqualTo(newValue);

		screenshotTrail();
    }

}
