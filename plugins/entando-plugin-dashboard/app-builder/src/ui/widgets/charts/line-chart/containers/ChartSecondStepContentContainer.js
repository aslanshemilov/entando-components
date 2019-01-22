import {connect} from "react-redux";
import {formValueSelector} from "redux-form";
import ChartSecondStepContent from "ui/widgets/charts/common/components/ChartSecondStepContent";

const selector = formValueSelector("form-dashboard-line-chart");

const mapStateToProps = (state, ownProps) => ({
  type: ownProps.type,
  data: ownProps.data,
  labelChartPreview: ownProps.labelChartPreview,
  axis: {rotated: selector(state, "axis.rotated")}
});

const ChartSecondStepContentContainer = connect(
  mapStateToProps,
  null
)(ChartSecondStepContent);
export default ChartSecondStepContentContainer;
