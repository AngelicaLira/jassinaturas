package sdk.jassinaturas.clients;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import co.freeside.betamax.Recorder;
import com.rodrigosaito.mockwebserver.player.Play;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import sdk.jassinaturas.Assinaturas;
import sdk.jassinaturas.clients.attributes.Authentication;
import sdk.jassinaturas.clients.attributes.Interval;
import sdk.jassinaturas.clients.attributes.Plan;
import sdk.jassinaturas.clients.attributes.PlanStatus;
import sdk.jassinaturas.clients.attributes.Trial;
import sdk.jassinaturas.clients.attributes.Unit;
import sdk.jassinaturas.communicators.ProductionCommunicator;
import sdk.jassinaturas.communicators.SandboxCommunicator;
import sdk.jassinaturas.exceptions.ApiResponseErrorException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlanClientTest {

    private final Assinaturas assinaturas = new Assinaturas(new Authentication("SGPA0K0R7O0IVLRPOVLJDKAWYBO1DZF3",
            "QUJESGM9JU175OGXRFRJIYM0SIFOMIFUYCBWH9WA"), new SandboxCommunicator());

    @Rule
    public Recorder recorder = new Recorder();

    @Play("ACTIVATE_PLAN")
    @Test
    public void shouldActivateAPlan() {

        Plan plan = assinaturas.plans().active("plan01");

        // There isn't any response from Moip Assinaturas when plan is activated
        // So, I didn't do any assert
    }

    @Play("CREATE_PLAN")
    @Test
    public void shouldCreateANewPlan() {
        Plan toCreate = new Plan();
        toCreate.withCode("plan001").withDescription("Plano de Teste").withName("Plano de Teste").withAmount(1000)
                .withSetupFee(100).withBillingCycles(1).withPlanStatus(PlanStatus.ACTIVE).withMaxQty(10)
                .withInterval(new Interval().withLength(10).withUnit(Unit.MONTH))
                .withTrial(new Trial().withDays(10).enabled());

        Plan created = assinaturas.plans().create(toCreate);

        assertEquals("Plano criado com sucesso", created.getMessage());
    }

    @Play("INACTIVATE_PLAN")
    @Test
    public void shouldInactivateAPlan() {

        Plan plan = assinaturas.plans().inactive("plan001");

        // There isn't any response from Moip Assinaturas when plan is
        // inactivated
        // So, I didn't do any assert
    }

    @Play("LIST_ALL_PLANS")
    @Test
    public void shouldListAllPlans() {
        List<Plan> plans = assinaturas.plans().list();
        assertEquals(7, plans.size());
    }

    @Play("CREATE_PLAN_RETURNED_ERROR")
    @Test
    public void shouldReturnErrors() {
        Plan toCreate = new Plan();
        toCreate.withCode("plan001").withDescription("Plano de Teste").withName("Plano de Teste").withAmount(1000)
                .withSetupFee(100).withBillingCycles(1).withPlanStatus(PlanStatus.ACTIVE).withMaxQty(10)
                .withInterval(new Interval().withLength(10).withUnit(Unit.MONTH))
                .withTrial(new Trial().withDays(10).enabled());

        try {
            Plan created = assinaturas.plans().create(toCreate);
            Assert.fail("Should return error");
        } catch (ApiResponseErrorException e) {
            assertEquals("Erro na requisição", e.getApiResponseError().getMessage());
            assertEquals("Código do plano já utilizado. Escolha outro código",
                    e.getApiResponseError().getErrors().get(0).getDescription());
            assertEquals("MA6", e.getApiResponseError().getErrors().get(0).getCode());
        }
    }

    @Play("GET_SINGLE_PLAN")
    @Test
    public void shouldShowAPlan() {
        Plan plan = assinaturas.plans().show("plan001");

        assertEquals("plan001", plan.getCode());
        assertEquals("Plano de Teste Atualizado", plan.getDescription());
        assertEquals("Plano de Teste Atualizado", plan.getName());
        assertEquals(10000, plan.getAmount());
        assertEquals(1000, plan.getSetupFee());
        assertEquals(10, plan.getBillingCycles());
        assertEquals(PlanStatus.INACTIVE, plan.getStatus());
        assertEquals(100, plan.getMaxQuantity());
        assertEquals(100, plan.getInterval().getLength());
        assertEquals(Unit.DAY, plan.getInterval().getUnit());
        assertFalse(plan.getTrial().isEnabled());
        assertEquals(5, plan.getTrial().getDays());
    }

    @Play("UPDATE_PLAN")
    @Test
    public void shouldUpdateAPlan() {
        Plan toUpdate = new Plan();
        toUpdate.withCode("plan001").withDescription("Plano de Teste Atualizado").withName("Plano de Teste Atualizado")
                .withAmount(10000).withSetupFee(1000).withBillingCycles(10).withPlanStatus(PlanStatus.INACTIVE)
                .withMaxQty(100).withInterval(new Interval().withLength(100).withUnit(Unit.DAY))
                .withTrial(new Trial().withDays(5).disabled());

        Plan plan = assinaturas.plans().update(toUpdate);

        // There isn't any response from Moip Assinaturas when plan is updated
        // So, I didn't do any assert

    }

    @Play("CREATE_PLAN_PRODUCTION")
    @Test
    public void shouldCreateANewPlanInProductionEnvironment() {
        Assinaturas assinaturas = new Assinaturas(new Authentication("SGPA0K0R7O0IVLRPOVLJDKAWYBO1DZF3",
                "QUJESGM9JU175OGXRFRJIYM0SIFOMIFUYCBWH9WA"), new ProductionCommunicator());

        Plan toCreate = new Plan();
        toCreate.withCode("plan_jassinaturas_production_01").withDescription("Plano de Teste")
                .withName("Plano de Teste").withAmount(1000).withSetupFee(100).withBillingCycles(1)
                .withPlanStatus(PlanStatus.ACTIVE).withMaxQty(10)
                .withInterval(new Interval().withLength(10).withUnit(Unit.MONTH))
                .withTrial(new Trial().withDays(10).enabled());

        Plan created = assinaturas.plans().create(toCreate);

        assertEquals("Plano criado com sucesso", created.getMessage());
    }

    @Play("GET_SINGLE_PLAN")
    @Test
    public void shouldGetResultFromToString() {
        String plan = assinaturas.plans().show("plan001").toString();

        assertEquals(
                "Plan [alerts=null, amount=10000, billingCycles=10, code=plan001, description=Plano de Teste Atualizado, interval=Interval [unit=DAY, length=100], maxQty=100, message=null, name=Plano de Teste Atualizado, plans=null, setupFee=1000, status=INACTIVE, trial=Trial [days=5, enabled=false]]",
                plan);
    }
}
