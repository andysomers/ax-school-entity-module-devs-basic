package com.foreach.across.samples.modules.invoice.domain.invoice;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "sample_invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Invoice extends SettableIdBasedEntity<Invoice> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sample_invoice_id")
    @GenericGenerator(
            name = "seq_sample_invoice_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_sample_invoice_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    @NotBlank
    @Column(name = "name")
    @Length(max = 255)
    private String name;

    @NotBlank
    @Column(name = "email")
    @Length(max = 255)
    private String email;

    @Column(name = "vat_number")
    @Length(max = 255)
    private String vatNumber;

    @NotNull
    @Column(name = "invoiceDate")
    private LocalDate invoiceDate;

    @NotNull
    @Column(name = "invoice_status")
    private InvoiceStatus invoiceStatus;

    /**
     * Create a label used by EntityModule
     *
     * @return String
     */
    public String getLabel() {
        return "invoice-" + this.getId();
    }
}
