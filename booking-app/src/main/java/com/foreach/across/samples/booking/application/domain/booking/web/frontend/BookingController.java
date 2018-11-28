package com.foreach.across.samples.booking.application.domain.booking.web.frontend;

import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.bootstrapui.elements.BootstrapUiElements;
import com.foreach.across.modules.entity.registry.properties.EntityPropertySelector;
import com.foreach.across.modules.entity.views.EntityViewElementBuilderHelper;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.entity.views.helpers.EntityViewElementBatch;
import com.foreach.across.modules.web.template.Template;
import com.foreach.across.modules.web.ui.elements.builder.ContainerViewElementBuilder;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.BookingRepository;
import com.foreach.across.samples.booking.application.domain.booking.TicketType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@Template(FrontendLayout.TEMPLATE)
@RequestMapping("/")
@RequiredArgsConstructor
class BookingController {
    private final BookingRepository bookingRepository;
    private final EntityViewElementBuilderHelper builderHelper;

    @GetMapping
    String bookingForm(@ModelAttribute("booking") Booking booking, Model model) {
        model.addAttribute("ticketTypes", TicketType.values());
        EntityViewElementBatch<Booking> elementBatch = builderHelper.createBatchForEntity(booking);
        elementBatch.setPropertySelector(EntityPropertySelector.of("name", "email", "numberOfTickets", "ticketType"));
        elementBatch.setViewElementMode(ViewElementMode.FORM_WRITE);
        Map<String, Object> builderHints = new HashMap<>();
        builderHints.put("ticketType", BootstrapUiElements.RADIO);
        elementBatch.setBuilderHints(builderHints);
        ContainerViewElementBuilder container = BootstrapUiBuilders.container().addAll(elementBatch.build().values());
        model.addAttribute("elements", container.build());
        return "th/booking/frontend/bookingForm";
    }

    @GetMapping("/thankyou")
    String bookingComplete() {
        return "th/booking/frontend/bookingComplete";
    }

    @PostMapping
    String submitBooking(@Validated @ModelAttribute("booking") Booking booking, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return bookingForm(booking, model);
        }

        booking.setCreated(ZonedDateTime.now());

        bookingRepository.save(booking);

        return "redirect:/thankyou";
    }
}
