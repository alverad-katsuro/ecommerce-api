package br.com.alverad.ecommerce.commons.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class SortingUtils {

    private SortingUtils() {}

    public static Sort createSorting(String[] sortByElements) {

        List<Order> orders = new ArrayList<>();

        if (sortByElements.length == 2 && !sortByElements[0].contains(",")) {
            return Sort.by(Direction.fromString(sortByElements[1]), sortByElements[0]);
        }

        for (String sortBy : sortByElements) {
            String[] sort = sortBy.split(",");
            if (sort.length == 1) {
                orders.add(Order.by(sort[0]));
            } else {
                orders.add(new Order(Direction.fromString(sort[1]), sort[0]));
            }

        }

        return Sort.by(orders);

    }

}
