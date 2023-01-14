import React from "react";
import { Spinner } from "react-bootstrap";

export const SpinnerWithMessage = ({ message }: SpinnerWithMessageProps) => {
	return (
		<>
			<Spinner animation="border" />
			<p>{message}</p>
		</>
	);
};

export type SpinnerWithMessageProps = {
	message?: string;
};
