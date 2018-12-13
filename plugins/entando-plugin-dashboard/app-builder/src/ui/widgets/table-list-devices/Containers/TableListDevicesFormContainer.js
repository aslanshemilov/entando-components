import {connect} from "react-redux";
import {initialize, change} from "redux-form";

import {fetchServerConfigList} from "state/main/actions";

import TableListDevicesForm from "ui/widgets/table-list-devices/Components/TableListDevicesForm";

const mapDispatchToProps = dispatch => ({
  onWillMount: () => {
    dispatch(fetchServerConfigList());
    dispatch(
      initialize("form-list-devices", {
        allColumns: true
      })
    );
  },
  onChangeToggleColumns: value => {
    if (value) {
      [
        "deviceStatus",
        "deviceUse",
        "batteryLevel",
        "deviceCode",
        "deviceBrand",
        "expirationGuarantee"
      ].forEach(item => {
        dispatch(change("form-list-devices", item, false));
      });
    }
  },
  onSubmit: values => {
    console.log("values", values);
  }
});

const TableListDevicesFormContainer = connect(
  null,
  mapDispatchToProps
)(TableListDevicesForm);

export default TableListDevicesFormContainer;
