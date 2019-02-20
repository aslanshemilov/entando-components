import {connect} from "react-redux";

import {updateDatasourceColumns, showHideColumn} from "state/main/actions";

import {getDatasourceColumns} from "state/main/selectors";

import DashboardTableColumns from "ui/widgets/table/components/DashboardTableColumns";

const mapStateToProps = state => ({
  columns: getDatasourceColumns(state)
});

const mapDispatchToProps = dispatch => ({
  onMoveColumn: columns =>
    dispatch(updateDatasourceColumns("form-dashboard-table", columns)),

  onShowHideColumn: key => dispatch(showHideColumn(key))
});
const DashboardTableColumnsContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(DashboardTableColumns);

export default DashboardTableColumnsContainer;