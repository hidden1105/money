package pay.jh.me.moneysprinkling.endity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "money_sprinkle_dtl")
public class MoneySprinkleDtl {
    public MoneySprinkleDtl(@NonNull String token) {
        this.token = token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moneySprinkleDtlSeq;

    @NonNull
    @ManyToOne(targetEntity = MoneySprinkle.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "token", insertable = false, updatable = false)
    private MoneySprinkle moneySprinkle;

    @NonNull
    private String token;

    @NonNull
    private double amount;

    @NonNull
    private String receiver;
    private LocalDateTime receivedAt;

    @Builder
    public MoneySprinkleDtl(double amount, String receiver) {
        this.amount = amount;
        this.receiver = receiver;
    }
}
