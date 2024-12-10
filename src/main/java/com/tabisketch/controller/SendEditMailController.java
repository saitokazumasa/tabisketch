package com.tabisketch.controller;

import com.tabisketch.bean.form.IsMatchPasswordForm;
import com.tabisketch.bean.form.SendEditMailForm;
import com.tabisketch.service.IIsExistMailService;
import com.tabisketch.service.IIsMatchPasswordService;
import com.tabisketch.service.ISendEditMailService;
import jakarta.mail.MessagingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/edit/mail")
public class SendEditMailController {
    private final IIsMatchPasswordService isMatchPasswordService;
    private final IIsExistMailService isExistMailService;
    private final ISendEditMailService sendEditMailService;

    public SendEditMailController(
            final IIsMatchPasswordService isMatchPasswordService,
            final IIsExistMailService isExistMailService,
            final ISendEditMailService sendEditMailService
    ) {
        this.isMatchPasswordService = isMatchPasswordService;
        this.isExistMailService = isExistMailService;
        this.sendEditMailService = sendEditMailService;
    }

    @GetMapping
    public String get(
            final @AuthenticationPrincipal UserDetails userDetails,
            final Model model
    ) {
        final var sendEditMailForm = SendEditMailForm.empty();
        sendEditMailForm.setCurrentMailAddress(userDetails.getUsername());
        model.addAttribute("sendEditMailForm", sendEditMailForm);
        return "user/edit/mail/index";
    }

    @PostMapping
    public String post(
            final @Validated SendEditMailForm sendEditMailForm,
            final BindingResult bindingResult,
            final @AuthenticationPrincipal UserDetails userDetails,
            final RedirectAttributes redirectAttributes
    ) throws MessagingException {
        // TODO: エラーメッセージ等、ベタ書きではなく別の場所から参照する形にする
        // パスワードが間違ってる、メールアドレスが存在する、は表示せずに通して、処理だけ実行しない方がセキュリティ的には良いかも？
        if (isNotMatchPassword(userDetails.getUsername(), sendEditMailForm.getCurrentPassword()))
            bindingResult.rejectValue("currentPassword", "error.editMailForm", "パスワードが一致しません");
        if (isNotExistMail(sendEditMailForm.getNewMailAddress()))
            bindingResult.rejectValue("newMail", "error.editMailForm", "メールアドレスが存在します。");
        if (bindingResult.hasErrors()) return "user/edit/mail/index";

        this.sendEditMailService.execute(sendEditMailForm);

        redirectAttributes.addFlashAttribute("mail", sendEditMailForm.getNewMailAddress());
        return "redirect:/user/edit/mail/send";
    }

    private boolean isNotMatchPassword(final String mail, final String password) {
        final var isMatchPasswordForm = new IsMatchPasswordForm(mail, password);
        return !this.isMatchPasswordService.execute(isMatchPasswordForm);
    }

    private boolean isNotExistMail(final String mail) {
        return this.isExistMailService.execute(mail);
    }

    @GetMapping("/send")
    public String send() {
        return "user/edit/mail/send";
    }
}
