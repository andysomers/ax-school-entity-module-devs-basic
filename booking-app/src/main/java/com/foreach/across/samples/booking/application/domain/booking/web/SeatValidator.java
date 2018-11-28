package com.foreach.across.samples.booking.application.domain.booking.web;

import com.foreach.across.modules.entity.validators.EntityValidatorSupport;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class SeatValidator extends EntityValidatorSupport<Seat> {
    private final SeatRepository seatRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Seat.class.isAssignableFrom(clazz);
    }

    @Override
    protected void postValidation(Seat entity, Errors errors, Object... validationHints) {
        String seatNumber = entity.getSeatNumber();
        Seat seat = seatRepository.findBySeatNumber(seatNumber);
        if (seat != null && !entity.equals(seat)) {
            errors.rejectValue("seatNumber", "alreadyExists");
        }
    }
}
