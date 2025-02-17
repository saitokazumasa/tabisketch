package com.tabisketch.controller;

import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.ICreatePlanService;
import com.tabisketch.service.ICreateWaypointListService;
import com.tabisketch.service.IEditPlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/plan/create")
public class CreatePlanController {
    private final ICreatePlanService createPlanService;
    private final IEditPlanService editPlanService;
    private final ICreateWaypointListService createWaypointListService;

    public CreatePlanController(
            final ICreatePlanService createPlanService,
            final IEditPlanService editPlanService,
            final ICreateWaypointListService createWaypointListService
    ) {
        this.createPlanService = createPlanService;
        this.editPlanService = editPlanService;
        this.createWaypointListService = createWaypointListService;
    }

    @GetMapping
    public String get(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final Model model
    ) {
        try {
            final EditPlanForm editPlanForm = this.createPlanService.execute(email);
            model.addAttribute("editPlanForm", editPlanForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "redirect:/plan/list";
        }

        model.addAttribute("createWaypointListForm", new CreateWaypointListForm());
        return "plan/create";
    }

    @PostMapping
    public String post(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final @Validated EditPlanForm editPlanForm,
            final BindingResult bindingResult1,
            final @Validated CreateWaypointListForm createWaypointListForm,
            final BindingResult bindingResult2,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult1.hasErrors()) return "plan/create";
        if (bindingResult2.hasErrors()) return "plan/create";

        try {
            // TODO: 経路最適化
            final EditPlanForm newEditPlanForm = this.editPlanService.execute(email, editPlanForm);
            final EditWaypointListForm editWaypointListForm = this.createWaypointListService.execute(email, createWaypointListForm);

            redirectAttributes.addFlashAttribute("editPlanForm", newEditPlanForm);
            redirectAttributes.addFlashAttribute("editWaypointListForm", editWaypointListForm);
            return "redirect:/plan/edit/" + newEditPlanForm.getUuid();
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "plan/create";
        }
    }
}
