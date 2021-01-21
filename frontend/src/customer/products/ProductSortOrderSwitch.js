import React from 'react';
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select";

export default function ProductSortOrderSwitch(props) {
    return (
        <Select fullWidth
                displayEmpty
                value={props.value}
                onChange={props.onChange}
                inputProps={{
                    name: 'sort_order',
                    id: 'products-sort-order-switch',
                }}>
            <MenuItem value={null}>bez sortowania</MenuItem>
            <MenuItem value={"price!asc"}>po cenie: rosnąco</MenuItem>
            <MenuItem value={"price!desc"}>po cenie: malejąco</MenuItem>
            <MenuItem value={"title!asc"}>po nazwie: rosnąco</MenuItem>
            <MenuItem value={"title!desc"}>po nazwie: malejąco</MenuItem>
        </Select>
    );
}