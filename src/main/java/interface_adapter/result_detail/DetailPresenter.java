package interface_adapter.result_detail;

public class DetailPresenter implements DetailOutputBoundary {

    private final DetailViewModel vm;

    public DetailPresenter(DetailViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void presentDetail(use_case.result_detail.DetailResponseModel response) {
        DetailState s = new DetailState(response.questions, response.options, response.correctIndex, response.savedAnswers);
        vm.setState(s);
    }
}
