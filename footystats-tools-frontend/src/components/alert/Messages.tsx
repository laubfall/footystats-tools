import React from "react";
import { Alert } from "react-bootstrap";
import { uniqueId } from "lodash";

export const Messages = ({ messages }: AlertProps) => {
	return (
		<>
			{messages?.map((m) => {
				return <Alert key={uniqueId()}>{m}</Alert>;
			})}
		</>
	);
};

export type AlertProps = {
	messages: string[];
};
