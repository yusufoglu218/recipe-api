package com.mendix.recipes.repository;

import com.google.common.base.Joiner;
import com.mendix.recipes.model.Recipe;
import com.mendix.recipes.util.SearchOperation;
import com.mendix.recipes.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RecipeSpecificationBuilder class to build SpecSearchCriteria
 */
public class RecipeSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public RecipeSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final RecipeSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final RecipeSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Recipe> build() {
        if (params.size() == 0)
            return null;

        Specification<Recipe> result = new RecipeSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new RecipeSpecification(params.get(i)))
                    : Specification.where(result).and(new RecipeSpecification(params.get(i)));
        }

        return result;
    }

    public final RecipeSpecificationsBuilder with(RecipeSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final RecipeSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }

    public final Specification<Recipe> buildBySearchCriteria(String searchCriteria){
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);

        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchCriteria + ",");
        while (matcher.find()) {
            with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        return build();
    }
}