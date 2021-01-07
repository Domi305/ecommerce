import React from "react";
export default function Price(props) {
    return (
        <em>{Number(props.value).toFixed(2)} PLN</em>
    );
}