import React from "react";
import { Alert } from "react-bootstrap";

export const Messages = ({ messages }: AlertProps) => {
	return (
		<>
			{messages?.map((m) => {
				return <Alert>{m}</Alert>;
			})}
		</>
	);
};

export type AlertProps = {
	messages: string[];
};
