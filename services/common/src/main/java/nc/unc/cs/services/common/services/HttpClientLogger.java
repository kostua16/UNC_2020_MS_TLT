package nc.unc.cs.services.common.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.ResponseData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HttpClientLogger
    implements LoadBalancerLifecycle<RequestDataContext, ResponseData, ServiceInstance> {

  @Override
  public void onStart(Request<RequestDataContext> request) {
  }

  @Override
  public void onStartRequest(
      Request<RequestDataContext> request, Response<ServiceInstance> lbResponse) {
    log.warn("Request STARTED: {}", request.getContext().getClientRequest().getUrl());
  }

  @Override
  public void onComplete(
      CompletionContext<ResponseData, ServiceInstance, RequestDataContext>
          completionContext) {
    if (completionContext.status().equals(CompletionContext.Status.FAILED)) {
      log.error(
          "Request FAILED({}): {}",
          completionContext.getClientResponse().getHttpStatus(),
          completionContext
              .getLoadBalancerRequest()
              .getContext()
              .getClientRequest()
              .getUrl());
    } else {
      log.warn(
          "Request SUCCESS({}): {}",
          completionContext.getClientResponse().getHttpStatus(),
          completionContext
              .getLoadBalancerRequest()
              .getContext()
              .getClientRequest()
              .getUrl());
    }
  }
}
