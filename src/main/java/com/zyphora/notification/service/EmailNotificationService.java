package com.zyphora.notification.service;

import com.zyphora.order.entity.OrderEntity;
import com.zyphora.order.entity.OrderItem;
import com.zyphora.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender      mailSender;
    private final OrderItemRepository orderItemRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${notify.email:true}")
    private boolean enabled;

    @Async
    public void sendOrderConfirmation(OrderEntity order) {
        if (!enabled) return;

        try {
            String toEmail = order.getUser().getEmail();
            if (toEmail == null || toEmail.isBlank()) return;

            List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("✅ Order Confirmed — #ZY" + String.format("%05d", order.getId()) + " | Zyphora");
            helper.setText(buildEmailHtml(order, items), true);

            mailSender.send(msg);
            log.info("📧 Order confirmation email sent to {}", toEmail);

        } catch (Exception e) {
            log.error("❌ Failed to send email: {}", e.getMessage());
        }
    }

    private String buildEmailHtml(OrderEntity order, List<OrderItem> items) {
        StringBuilder rows = new StringBuilder();
        for (OrderItem item : items) {

    rows.append("""
        <tr>
          <td style="padding:10px;border-bottom:1px solid #f0f0f0;">%s</td>
          <td style="padding:10px;border-bottom:1px solid #f0f0f0;text-align:center;">%d</td>
          <td style="padding:10px;border-bottom:1px solid #f0f0f0;text-align:right;font-weight:bold;">₹%s</td>
        </tr>
        """.formatted(
            item.getProduct().getTitle(),
            item.getQuantity(),
            item.getPrice().multiply(
                java.math.BigDecimal.valueOf(item.getQuantity())
            )
    ));
}

        return """
            <!DOCTYPE html>
            <html>
            <body style="margin:0;padding:0;font-family:'Segoe UI',sans-serif;background:#f5f5f5;">
              <div style="max-width:580px;margin:30px auto;background:#fff;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);">
                
                <!-- Header -->
                <div style="background:linear-gradient(135deg,#4F46E5,#EC4899);padding:32px;text-align:center;">
                  <h1 style="color:#fff;margin:0;font-size:26px;letter-spacing:-0.5px;">⚡ Zyphora</h1>
                  <p style="color:rgba(255,255,255,0.85);margin:8px 0 0;font-size:15px;">Your order is confirmed!</p>
                </div>
                
                <!-- Body -->
                <div style="padding:30px;">
                  <p style="font-size:16px;color:#333;">Hello <strong>%s</strong>,</p>
                  <p style="color:#666;line-height:1.6;">Thank you for shopping with Zyphora! Your order has been placed successfully and is being processed.</p>
                  
                  <!-- Order Info -->
                  <div style="background:#f8f9ff;border-radius:12px;padding:18px;margin:20px 0;">
                    <p style="margin:0 0 8px;font-size:13px;color:#888;font-weight:600;text-transform:uppercase;letter-spacing:0.5px;">Order Details</p>
                    <p style="margin:4px 0;font-size:15px;"><strong>Order ID:</strong> #ZY%s</p>
                    <p style="margin:4px 0;font-size:15px;"><strong>Payment:</strong> %s</p>
                    <p style="margin:4px 0;font-size:15px;"><strong>Status:</strong> <span style="color:#22c55e;font-weight:bold;">%s</span></p>
                  </div>
                  
                  <!-- Items Table -->
                  <table style="width:100%%;border-collapse:collapse;margin:20px 0;">
                    <thead>
                      <tr style="background:#f8f9ff;">
                        <th style="padding:10px;text-align:left;font-size:12px;color:#888;text-transform:uppercase;">Product</th>
                        <th style="padding:10px;text-align:center;font-size:12px;color:#888;text-transform:uppercase;">Qty</th>
                        <th style="padding:10px;text-align:right;font-size:12px;color:#888;text-transform:uppercase;">Price</th>
                      </tr>
                    </thead>
                    <tbody>
                      %s
                    </tbody>
                  </table>
                  
                  <!-- Total -->
                  <div style="background:linear-gradient(135deg,#4F46E5,#EC4899);border-radius:12px;padding:16px 20px;text-align:right;">
                    <p style="margin:0;color:rgba(255,255,255,0.8);font-size:13px;">Total Amount</p>
                    <p style="margin:4px 0 0;color:#fff;font-size:24px;font-weight:bold;">₹%s</p>
                  </div>
                  
                  <!-- Delivery Address -->
                  <div style="margin-top:20px;padding:16px;border:1px solid #e5e7eb;border-radius:12px;">
                    <p style="margin:0 0 8px;font-size:13px;color:#888;font-weight:600;text-transform:uppercase;letter-spacing:0.5px;">📦 Delivery Address</p>
                    <p style="margin:0;color:#333;line-height:1.7;">
                      %s<br>%s, %s %s<br>%s<br>📱 %s
                    </p>
                  </div>
                  
                  <p style="color:#888;font-size:13px;margin-top:24px;text-align:center;">Questions? Email us at <a href="mailto:support@zyphora.com" style="color:#4F46E5;">support@zyphora.com</a></p>
                </div>
                
                <!-- Footer -->
                <div style="background:#f8f9ff;padding:20px;text-align:center;">
                  <p style="margin:0;color:#999;font-size:12px;">© 2025 Zyphora. All rights reserved.</p>
                </div>
              </div>
            </body>
            </html>
            """.formatted(
                order.getUser().getFullName() != null ? order.getUser().getFullName() : "Customer",
                String.format("%05d", order.getId()),
                order.getPaymentMethod(),
                order.getStatus().name(),
                rows.toString(),
                order.getTotalAmount().toPlainString(),
                order.getAddress().getLine1(),
                order.getAddress().getCity(),
                order.getAddress().getState(),
                order.getAddress().getPincode(),
                order.getAddress().getCountry() != null ? order.getAddress().getCountry() : "India",
                order.getAddress().getMobile()
        );
    }
}

