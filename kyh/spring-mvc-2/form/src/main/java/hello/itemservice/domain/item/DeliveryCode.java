package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 배송방식을 위한 객체 <br>
 * <ul>
 *     <li>FAST : 빠른 배송</li>
 *     <li>NORMAL : 일반배송</li>
 *     <li>SLOW : 느린배송</li>
 * </ul>
 *
 * @author na seungchul
 */
@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code;
    private String displayName;
}
