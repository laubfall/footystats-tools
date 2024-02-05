import React, { useEffect, useState } from "react";
import * as yup from "yup";
import { Formik, FormikValues } from "formik";
import { Button, Container, Form } from "react-bootstrap";
import translate from "../../i18n/translate";
import { SettingsControllerApi } from "../../footystats-frontendapi";
import { apiCatchReasonHandler } from "../functions";
import AlertMessagesStore from "../../mobx/AlertMessages";

export const SettingsView = () => {
	const sca = new SettingsControllerApi();

	const validationSchema = yup.object({
		footyusername: yup
			.string()
			.required(
				translate("renderer.settings.input.footyusername.required"),
			),
		footypassword: yup
			.string()
			.required(
				translate("renderer.settings.input.footypassword.required"),
			),
	});

	const [initialValues, setInitialValues] = useState({
		footyusername: "",
		footypassword: "",
	});

	const handleSubmit = (values: FormikValues) => {
		sca.saveSettings({
			settings: {
				footyStatsUsername: values.footyusername,
				footyStatsPassword: {
					password: values.footypassword,
				},
			},
		})
			.then(() => {
				AlertMessagesStore.addMessage(
					translate("renderer.settings.save.success"),
				);
			})
			.catch(apiCatchReasonHandler);
	};

	useEffect(() => {
		sca.loadSettings()
			.then((response) =>
				setInitialValues({
					...initialValues,
					footyusername: response.footyStatsUsername,
					footypassword: response.footyStatsPassword?.password,
				}),
			)
			.catch(apiCatchReasonHandler);
	}, []);

	return (
		<Container>
			<Formik
				initialValues={initialValues}
				onSubmit={handleSubmit}
				validationSchema={validationSchema}
				enableReinitialize={true}
			>
				{({ values, errors, handleChange, handleSubmit }) => (
					<Form onSubmit={handleSubmit} noValidate>
						<Form.Group className="mb-3" controlId="footyusername">
							<Form.Label>
								{translate(
									"renderer.settings.input.footyusername",
								)}
							</Form.Label>
							<Form.Control
								type="text"
								onChange={handleChange}
								value={values.footyusername}
								isInvalid={!!errors.footyusername}
							/>
							<Form.Control.Feedback type="invalid">
								{errors.footyusername as string}
							</Form.Control.Feedback>
						</Form.Group>

						<Form.Group className="mb-3" controlId="footypassword">
							<Form.Label>
								{translate(
									"renderer.settings.input.footypassword",
								)}
							</Form.Label>
							<Form.Control
								type="password"
								value={values.footypassword}
								onChange={handleChange}
								isInvalid={!!errors.footypassword}
							/>
							<Form.Control.Feedback type="invalid">
								{errors.footypassword as string}
							</Form.Control.Feedback>
						</Form.Group>

						<Button variant="primary" type="submit">
							Submit
						</Button>
					</Form>
				)}
			</Formik>
		</Container>
	);
};
