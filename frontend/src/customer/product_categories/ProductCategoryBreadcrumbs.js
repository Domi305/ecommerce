import React from 'react';
import {Breadcrumbs, Typography} from '@material-ui/core';

export default function ProductCategoryBreadcrumbs(props) {
        return (
            <Breadcrumbs aria-label="breadcrumb">
                <Typography color="textPrimary">Caly asortyment</Typography>
                {props.nodes.map(node => <Typography key={node.id} color="textPrimary">{node.name}</Typography>)}
            </Breadcrumbs>
        );
}