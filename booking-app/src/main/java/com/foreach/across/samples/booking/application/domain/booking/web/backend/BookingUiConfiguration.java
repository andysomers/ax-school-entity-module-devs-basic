package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.bootstrapui.elements.TextboxFormElement;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.query.EntityQueryConditionTranslator;
import com.foreach.across.modules.entity.registry.EntityAssociation;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyController;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyHandlingType;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyValidator;
import com.foreach.across.modules.entity.views.EntityViewCustomizers;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.SeatRepository;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceRepository;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Validator;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BookingUiConfiguration implements EntityConfigurer {
    private final SeatRepository seatRepository;
    private final Validator entityValidator;
    private final InvoiceRepository invoiceRepository;

    @EventListener
    public void adminMenu(AdminMenuEvent menu) {
        menu.item("/entities/BookingApplicationModule").changePathTo("/BookingApplicationModule");
    }

    @Override
    public void configure(EntitiesConfigurationBuilder entities) {
        entities.withType(Seat.class)
                .label("seatNumber");
        entities.withType(Booking.class)
                .properties(
                        reg -> reg.property("seats")
                                .propertyType(TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Seat.class)))
                                .valueFetcher(seatRepository::findAllByBooking)
                                .and()
                                .property("invoice")
                                .attribute(EntityPropertyHandlingType.class, EntityPropertyHandlingType.BINDER)
                                .controller(
                                        ctl -> ctl.withTarget(Booking.class, Invoice.class)
                                                .validator(EntityPropertyValidator.of(entityValidator))
                                                .createValueFunction(
                                                        booking -> Invoice.builder()
                                                                .email(booking.getEmail())
                                                                .name(booking.getName())
                                                                .amount((double) (booking.getNumberOfTickets() * 10))
                                                                .invoiceStatus(InvoiceStatus.SENT)
                                                                .build()
                                                )
                                                .saveConsumer((booking, invoiceEntityPropertyValue) -> {
                                                    Invoice saved = invoiceRepository.save(invoiceEntityPropertyValue.getNewValue());
                                                    booking.setInvoice(saved);
                                                })
                                                .order(EntityPropertyController.BEFORE_ENTITY)
                                )
                                .and()
                                .property("searchText")
                                .propertyType(String.class)
                                .attribute(EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.expandingOr("name", "email"))
                                .and()
                                .property("name")
                                .attribute(EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.ignoreCase())
                                .and()
                                .property("email")
                                .attribute(EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.ignoreCase())
                                .and()
                                .property("followupList[].remarks").attribute(TextboxFormElement.Type.class, TextboxFormElement.Type.TEXT)

                )
                .formView("invoiceView",
                        EntityViewCustomizers.basicSettings().adminMenu("/invoice")
                                .andThen(
                                        fvb -> fvb.showProperties("invoice.*")
                                )
                )
                .association(
                        ass -> ass.name("seat.booking")
                                .associationType(EntityAssociation.Type.EMBEDDED)
                                .parentDeleteMode(EntityAssociation.ParentDeleteMode.WARN)
                )
                .listView(
                        lvb -> lvb.showProperties("*", "~ticketType")
                                .defaultSort(new Sort(Sort.Direction.DESC, "created"))
                                .attribute(EntityAttributes.LINK_TO_DETAIL_VIEW, true)
                                .entityQueryFilter(
                                        eql -> eql
                                                .showProperties("searchText", "ticketType")
                                                .multiValue("ticketType")
                                )
                )
                .createFormView(
                        cfv -> cfv
                                .showProperties(".", "~invoice")
                                .properties(
                                        reg -> reg.property("created")
                                                .attribute(EntityAttributes.PROPERTY_REQUIRED, true)
                                ))
                .updateFormView(
                        ufv -> ufv
                                .showProperties(".", "created", "~invoice")
                                .properties(
                                        reg -> reg.property("created").writable(false)
                                ));
    }
}
